package com.example.shahicripto.features.marketScreen.aiSignals

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import cn.pedant.SweetAlert.SweetAlertDialog
import com.bumptech.glide.RequestManager
import com.example.shahicripto.R
import com.example.shahicripto.databinding.FragmentAiSignalsBinding
import com.example.shahicripto.model.MainRepository
import com.example.shahicripto.model.MyDatabase
import com.example.shahicripto.model.api.ApiService
import com.example.shahicripto.model.local.AiEngine
import com.example.shahicripto.model.local.AiSignalResult
import com.example.shahicripto.model.local.SignalAction
import com.example.shahicripto.model.local.TimeframeOption
import com.example.shahicripto.model.local.CoinsData.CoinsDataEntitity
import com.example.shahicripto.util.PaymentReceipt
import com.example.shahicripto.util.UserSubscriptionManager
import com.example.shahicripto.util.formatCryptoPrice
import com.example.shahicripto.util.showToast
import dagger.hilt.android.AndroidEntryPoint
import java.text.NumberFormat
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class AiSignalsFragment : Fragment() {

    private var _binding: FragmentAiSignalsBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var glide: RequestManager

    @Inject
    lateinit var apiService: ApiService

    private lateinit var viewModel: AiSignalsViewModel
    private var coinChipAdapter: CoinChipAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAiSignalsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val context = requireContext()
        val db = MyDatabase.getDatabase(context)
        val repository = MainRepository(
            apiService,
            db.coinsDataDao,
            db.newsDataDao,
            db.coinCatalogDao,
            db.priceSnapshotDao
        )
        val subscriptionManager = UserSubscriptionManager(context)

        viewModel = ViewModelProvider(
            this,
            AiSignalsViewModelFactory(repository, subscriptionManager)
        )[AiSignalsViewModel::class.java]

        viewModel.syncRemoteConfig(context)

        setupPaywall()
        setupDashboard()
        observeViewModel()
    }

    private fun isPersian(): Boolean {
        return resources.configuration.locales[0].language == "fa"
    }

    private fun formatRials(amount: Long): String {
        return NumberFormat.getNumberInstance(Locale.US).format(amount)
    }

    private fun setupPaywall() {
        binding.btnStartFreeTrial.setOnClickListener {
            val name = binding.edtName.text?.toString().orEmpty().trim()
            val phone = binding.edtPhone.text?.toString().orEmpty().trim()
            val email = binding.edtEmail.text?.toString().orEmpty().trim()

            if (name.isBlank()) {
                context?.showToast(getString(R.string.fill_all_fields))
                binding.edtName.requestFocus()
                return@setOnClickListener
            }

            if (phone.isBlank() || phone.length < 10) {
                context?.showToast(getString(R.string.invalid_phone))
                binding.edtPhone.requestFocus()
                return@setOnClickListener
            }

            // Start 3-Day Free VIP Trial
            viewModel.startFreeTrial(name = name, phone = phone, email = email)

            val dialog = SweetAlertDialog(requireContext(), SweetAlertDialog.SUCCESS_TYPE)
            dialog.titleText = getString(R.string.trial_activated_title)
            dialog.contentText = getString(R.string.trial_activated_msg)
            dialog.confirmText = getString(R.string.ok)
            dialog.show()
        }

        binding.btnActivateVip.setOnClickListener {
            val name = binding.edtName.text?.toString().orEmpty().trim()
            val phone = binding.edtPhone.text?.toString().orEmpty().trim()
            val email = binding.edtEmail.text?.toString().orEmpty().trim()

            if (name.isBlank()) {
                context?.showToast(getString(R.string.fill_all_fields))
                binding.edtName.requestFocus()
                return@setOnClickListener
            }

            if (phone.isBlank() || phone.length < 10) {
                context?.showToast(getString(R.string.invalid_phone))
                binding.edtPhone.requestFocus()
                return@setOnClickListener
            }

            val (planName, amountRials) = when (binding.rgPlans.checkedRadioButtonId) {
                R.id.rb_plan_1m -> {
                    val pName = if (isPersian()) "اشتراک ۱ ماهه VIP" else "1 Month VIP"
                    Pair(pName, 4_900_000L)
                }
                R.id.rb_plan_1y -> {
                    val pName = if (isPersian()) "اشتراک ۱ ساله VIP" else "1 Year VIP"
                    Pair(pName, 39_000_000L)
                }
                else -> {
                    val pName = if (isPersian()) "اشتراک ۳ ماهه VIP" else "3 Months VIP"
                    Pair(pName, 11_900_000L)
                }
            }

            val gateway = if (isPersian()) "درگاه پرداخت الکترونیک زرین‌پال (شاپرک)" else "ZarinPal Official Gateway"

            showPaymentGatewayDialog(name, phone, email, planName, amountRials, gateway)
        }
    }

    private fun showPaymentGatewayDialog(
        name: String,
        phone: String,
        email: String,
        plan: String,
        amountRials: Long,
        gateway: String
    ) {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_iranian_payment_gateway)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        val txtGatewayTitle = dialog.findViewById<TextView>(R.id.txt_gateway_title)
        val txtGatewaySubtitle = dialog.findViewById<TextView>(R.id.txt_gateway_subtitle)
        val txtAmountRials = dialog.findViewById<TextView>(R.id.txt_dialog_amount_rials)
        val txtAmountTomans = dialog.findViewById<TextView>(R.id.txt_dialog_amount_tomans)
        val txtInvoiceId = dialog.findViewById<TextView>(R.id.txt_invoice_id)
        val txtDetectedBank = dialog.findViewById<TextView>(R.id.txt_detected_bank)

        val edtCardNumber = dialog.findViewById<EditText>(R.id.edt_card_number)
        val edtCvv2 = dialog.findViewById<EditText>(R.id.edt_cvv2)
        val edtExpMonth = dialog.findViewById<EditText>(R.id.edt_exp_month)
        val edtExpYear = dialog.findViewById<EditText>(R.id.edt_exp_year)
        val edtDynamicPin = dialog.findViewById<EditText>(R.id.edt_dynamic_pin)
        val edtCaptcha = dialog.findViewById<EditText>(R.id.edt_captcha)
        val txtCaptchaCode = dialog.findViewById<TextView>(R.id.txt_captcha_code)

        val btnRequestDynamicPin = dialog.findViewById<Button>(R.id.btn_request_dynamic_pin)
        val btnPay = dialog.findViewById<Button>(R.id.btn_dialog_pay)
        val btnCancel = dialog.findViewById<Button>(R.id.btn_dialog_cancel)

        txtGatewayTitle.text = "درگاه پرداخت شاپرک • زرین‌پال"
        txtGatewaySubtitle.text = "پذیرنده رسمی: کریپتوپدیا VIP (تضمین امنیت زرین‌پال)"

        val formattedRials = formatRials(amountRials)
        val formattedTomans = formatRials(amountRials / 10)
        txtAmountRials.text = "$formattedRials ریال"
        txtAmountTomans.text = "معادل $formattedTomans تومان"

        val invoiceId = "CP-${(10000..99999).random()}"
        txtInvoiceId.text = "شماره فاکتور: $invoiceId"

        // Sample initial card data for seamless user testing
        edtCardNumber.setText("6037991847203185")
        edtCvv2.setText("382")
        edtExpMonth.setText("10")
        edtExpYear.setText("29")

        // Captcha
        val captcha = (10000..99999).random().toString()
        txtCaptchaCode.text = captcha
        edtCaptcha.setText(captcha)

        // Real-time Bank detection
        edtCardNumber.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val clean = s?.toString()?.replace("-", "")?.replace(" ", "").orEmpty()
                txtDetectedBank.text = detectBankName(clean)
            }
            override fun afterTextChanged(s: Editable?) {}
        })
        txtDetectedBank.text = detectBankName("603799")

        btnRequestDynamicPin.setOnClickListener {
            val generatedPin = (100000..999999).random().toString()
            edtDynamicPin.setText(generatedPin)
            context?.showToast("رمز پویا ($generatedPin) به شماره $phone پیامک شد.")
        }

        btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        btnPay.setOnClickListener {
            val card = edtCardNumber.text?.toString()?.trim().orEmpty().replace("-", "").replace(" ", "")
            val cvv2 = edtCvv2.text?.toString()?.trim().orEmpty()
            val pin = edtDynamicPin.text?.toString()?.trim().orEmpty()

            if (card.length < 16) {
                context?.showToast(getString(R.string.invalid_card))
                edtCardNumber.requestFocus()
                return@setOnClickListener
            }

            if (cvv2.length < 3) {
                context?.showToast("لطفاً CVV2 معتبر وارد کنید")
                edtCvv2.requestFocus()
                return@setOnClickListener
            }

            if (pin.isBlank()) {
                context?.showToast(getString(R.string.invalid_pin))
                edtDynamicPin.requestFocus()
                return@setOnClickListener
            }

            val refId = "10${(100000..999999).random()}"

            viewModel.activateSubscription(
                name = name,
                phone = phone,
                email = email,
                plan = plan,
                amountRials = amountRials,
                gateway = gateway,
                refId = refId,
                invoiceId = invoiceId
            )

            dialog.dismiss()
            showPaymentReceiptDialog(viewModel.getReceipt())
        }

        dialog.show()
    }

    private fun detectBankName(cardPrefix: String): String {
        return when {
            cardPrefix.startsWith("603799") -> "بانک ملی ایران (شتاب)"
            cardPrefix.startsWith("610433") -> "بانک ملت (شتاب)"
            cardPrefix.startsWith("589210") -> "بانک سپه (شتاب)"
            cardPrefix.startsWith("621986") -> "بانک سامان (شتاب)"
            cardPrefix.startsWith("502229") -> "بانک پاسارگاد (شتاب)"
            cardPrefix.startsWith("627412") -> "بانک اقتصاد نوین (شتاب)"
            cardPrefix.startsWith("603770") -> "بانک کشاورزی (شتاب)"
            cardPrefix.startsWith("628023") -> "بانک مسکن (شتاب)"
            cardPrefix.startsWith("504706") -> "بانک شهر (شتاب)"
            cardPrefix.startsWith("627381") -> "بانک انصار (شتاب)"
            cardPrefix.startsWith("639346") -> "بانک سینا (شتاب)"
            cardPrefix.startsWith("502908") -> "بانک توسعه تعاون (شتاب)"
            else -> "کارت بانکی عضو شبکه شتاب شاپرک"
        }
    }

    private fun showPaymentReceiptDialog(receipt: PaymentReceipt) {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_payment_receipt)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        dialog.findViewById<TextView>(R.id.txt_receipt_ref_id)?.text = receipt.refId
        dialog.findViewById<TextView>(R.id.txt_receipt_invoice_id)?.text = receipt.invoiceId

        val formattedRials = formatRials(receipt.amountRials)
        val formattedTomans = formatRials(receipt.amountTomans)
        dialog.findViewById<TextView>(R.id.txt_receipt_amount)?.text = "$formattedRials ریال ($formattedTomans تومان)"
        dialog.findViewById<TextView>(R.id.txt_receipt_gateway)?.text = receipt.gatewayName
        dialog.findViewById<TextView>(R.id.txt_receipt_plan)?.text = receipt.planName

        dialog.findViewById<Button>(R.id.btn_receipt_close)?.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun setupDashboard() {
        binding.btnViewReceipt.setOnClickListener {
            showPaymentReceiptDialog(viewModel.getReceipt())
        }

        binding.btnManageSubscription.setOnClickListener {
            val dialog = SweetAlertDialog(requireContext(), SweetAlertDialog.WARNING_TYPE)
            dialog.titleText = getString(R.string.logout_subscription)
            dialog.contentText = getString(R.string.sign_out) + "?"
            dialog.confirmText = getString(R.string.ok)
            dialog.cancelText = getString(R.string.cancel)
            dialog.setConfirmClickListener {
                dialog.dismiss()
                viewModel.signOut()
            }
            dialog.show()
        }

        // Setup AI Engine Toggle (Gemini vs ChatGPT)
        binding.toggleAiEngine.check(R.id.btn_engine_gemini)
        binding.toggleAiEngine.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                val engine = when (checkedId) {
                    R.id.btn_engine_chatgpt -> AiEngine.CHATGPT
                    else -> AiEngine.GEMINI
                }
                viewModel.setEngine(engine, isFa = isPersian())
            }
        }

        // Setup Timeframe Toggle (1H, 1D, 1W, 1M, 1Y)
        binding.toggleTimeframe.check(R.id.btn_tf_1d)
        binding.toggleTimeframe.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                val tf = when (checkedId) {
                    R.id.btn_tf_1h -> TimeframeOption.ONE_HOUR
                    R.id.btn_tf_1w -> TimeframeOption.ONE_WEEK
                    R.id.btn_tf_1m -> TimeframeOption.ONE_MONTH
                    R.id.btn_tf_1y -> TimeframeOption.ONE_YEAR
                    else -> TimeframeOption.ONE_DAY
                }
                viewModel.setTimeframe(tf, isFa = isPersian())
            }
        }

        binding.btnReanalyze.setOnClickListener {
            viewModel.reanalyze(isFa = isPersian())
        }
    }

    private fun observeViewModel() {
        viewModel.isVipActive.observe(viewLifecycleOwner) { isActive ->
            if (isActive) {
                binding.containerPaywall.visibility = View.GONE
                binding.containerDashboard.visibility = View.VISIBLE
                val userName = viewModel.getUserName().ifBlank { "کاربر VIP" }
                val isTrial = viewModel.isTrialUser()
                val plan = if (isTrial) {
                    val daysLeft = viewModel.getRemainingTrialDays()
                    getString(R.string.trial_active, daysLeft)
                } else {
                    viewModel.getPlanName()
                }
                binding.txtVipUserName.text = userName
                binding.txtVipPlanInfo.text = plan
            } else {
                binding.containerPaywall.visibility = View.VISIBLE
                binding.containerDashboard.visibility = View.GONE
            }
        }

        viewModel.dailyQuotaUsed.observe(viewLifecycleOwner) { used ->
            val isFa = isPersian()
            val total = 20
            val remaining = (total - used).coerceAtLeast(0)
            if (isFa) {
                binding.txtDailyQuotaCount.text = "$used از $total تحلیل استفاده شده"
                binding.txtDailyQuotaStatus.text = "سهمیه روزانه: $remaining تحلیل باقی‌مانده است"
            } else {
                binding.txtDailyQuotaCount.text = "$used / $total used"
                binding.txtDailyQuotaStatus.text = "Daily Quota: $remaining analyses left today"
            }
        }

        viewModel.quotaExceededEvent.observe(viewLifecycleOwner) { exceeded ->
            if (exceeded == true) {
                showDailyLimitReachedDialog()
            }
        }

        viewModel.coinsList.observe(viewLifecycleOwner) { coins ->
            if (coins.isNotEmpty()) {
                val selected = viewModel.selectedCoin.value ?: coins.first()
                if (viewModel.selectedCoin.value == null) {
                    viewModel.selectCoin(selected, isFa = isPersian())
                }

                val selectedIdx = coins.indexOfFirst { it.coinId == selected.coinId }.coerceAtLeast(0)
                coinChipAdapter = CoinChipAdapter(
                    glide = glide,
                    coins = coins,
                    selectedIndex = selectedIdx,
                    onCoinSelected = { coin ->
                        viewModel.selectCoin(coin, isFa = isPersian())
                    }
                )
                binding.recyclerCoinsSelector.adapter = coinChipAdapter
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { loading ->
            binding.layoutSignalLoading.visibility = if (loading) View.VISIBLE else View.GONE
            binding.layoutSignalContent.visibility = if (loading) View.GONE else View.VISIBLE
        }

        viewModel.signalResult.observe(viewLifecycleOwner) { signal ->
            if (signal != null) {
                displaySignal(signal)
            }
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { error ->
            if (!error.isNullOrBlank()) {
                context?.showToast(error)
            }
        }
    }

    private fun showDailyLimitReachedDialog() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_daily_limit_reached)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        dialog.findViewById<Button>(R.id.btn_limit_dialog_ok)?.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun displaySignal(signal: AiSignalResult) {
        val isFa = isPersian()
        binding.txtSignalCoinName.text = "${signal.coinName} (${signal.coinSymbol})"
        binding.txtSignalTimeframeBadge.text = "${getString(R.string.timeframe)}: ${signal.timeframe.getDisplayName(isFa)}"
        binding.txtSignalConfidenceBadge.text = "${signal.confidencePercent}% ${getString(R.string.confidence)}"

        // Load Coin Logo
        val symbol = signal.coinSymbol.lowercase(Locale.US)
        val coincapUrl = "https://assets.coincap.io/assets/icons/$symbol@2x.png"
        val spothqUrl = "https://cdn.jsdelivr.net/gh/spothq/cryptocurrency-icons@master/128/color/$symbol.png"

        glide.load(coincapUrl)
            .placeholder(R.drawable.ic_coin_placeholder)
            .error(
                glide.load(spothqUrl)
                    .placeholder(R.drawable.ic_coin_placeholder)
                    .error(R.drawable.ic_coin_placeholder)
            )
            .into(binding.imgSignalCoin)

        // Decision Action Color & Banner
        val actionColor = try {
            Color.parseColor(signal.action.colorHex)
        } catch (_: Exception) {
            ContextCompat.getColor(requireContext(), R.color.colorGain)
        }

        binding.cardRecommendation.strokeColor = actionColor
        binding.cardActionBanner.setCardBackgroundColor(actionColor)
        binding.txtActionRecommendation.text = signal.action.getDisplayLabel(isFa)

        // Prices
        binding.txtSignalCurrentPrice.text = formatCryptoPrice(signal.currentPrice)
        binding.txtSignalTargetPrice.text = formatCryptoPrice(signal.targetPrice)
        binding.txtSignalStopLoss.text = formatCryptoPrice(signal.stopLoss)

        // Summaries & Rationale
        binding.txtTechnicalSummary.text = signal.technicalSummary
        binding.txtFundamentalSummary.text = signal.fundamentalSummary
        binding.txtAiRationale.text = signal.aiRationale
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
