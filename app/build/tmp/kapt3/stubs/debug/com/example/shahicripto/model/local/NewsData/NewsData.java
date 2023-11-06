package com.example.shahicripto.model.local.NewsData;

import java.lang.System;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u001a\b\u0086\b\u0018\u00002\u00020\u0001:\u0002%&BA\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u00a2\u0006\u0002\u0010\u000eJ\u000f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00c6\u0003J\t\u0010\u001b\u001a\u00020\u0006H\u00c6\u0003J\t\u0010\u001c\u001a\u00020\bH\u00c6\u0003J\u000f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003H\u00c6\u0003J\t\u0010\u001e\u001a\u00020\u000bH\u00c6\u0003J\t\u0010\u001f\u001a\u00020\rH\u00c6\u0003JQ\u0010 \u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\b2\u000e\b\u0002\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00010\u00032\b\b\u0002\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\f\u001a\u00020\rH\u00c6\u0001J\u0013\u0010!\u001a\u00020\u00062\b\u0010\"\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010#\u001a\u00020\rH\u00d6\u0001J\t\u0010$\u001a\u00020\bH\u00d6\u0001R\u001c\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0016\u0010\u0005\u001a\u00020\u00068\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0016\u0010\u0007\u001a\u00020\b8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u001c\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00010\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0010R\u0016\u0010\n\u001a\u00020\u000b8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0016\u0010\f\u001a\u00020\r8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019\u00a8\u0006\'"}, d2 = {"Lcom/example/shahicripto/model/local/NewsData/NewsData;", "", "data", "", "Lcom/example/shahicripto/model/local/NewsData/NewsData$Data;", "hasWarning", "", "message", "", "promoted", "rateLimit", "Lcom/example/shahicripto/model/local/NewsData/NewsData$RateLimit;", "type", "", "(Ljava/util/List;ZLjava/lang/String;Ljava/util/List;Lcom/example/shahicripto/model/local/NewsData/NewsData$RateLimit;I)V", "getData", "()Ljava/util/List;", "getHasWarning", "()Z", "getMessage", "()Ljava/lang/String;", "getPromoted", "getRateLimit", "()Lcom/example/shahicripto/model/local/NewsData/NewsData$RateLimit;", "getType", "()I", "component1", "component2", "component3", "component4", "component5", "component6", "copy", "equals", "other", "hashCode", "toString", "Data", "RateLimit", "app_debug"})
public final class NewsData {
    @org.jetbrains.annotations.NotNull
    @com.google.gson.annotations.SerializedName(value = "Data")
    private final java.util.List<com.example.shahicripto.model.local.NewsData.NewsData.Data> data = null;
    @com.google.gson.annotations.SerializedName(value = "HasWarning")
    private final boolean hasWarning = false;
    @org.jetbrains.annotations.NotNull
    @com.google.gson.annotations.SerializedName(value = "Message")
    private final java.lang.String message = null;
    @org.jetbrains.annotations.NotNull
    @com.google.gson.annotations.SerializedName(value = "Promoted")
    private final java.util.List<java.lang.Object> promoted = null;
    @org.jetbrains.annotations.NotNull
    @com.google.gson.annotations.SerializedName(value = "RateLimit")
    private final com.example.shahicripto.model.local.NewsData.NewsData.RateLimit rateLimit = null;
    @com.google.gson.annotations.SerializedName(value = "Type")
    private final int type = 0;
    
    @org.jetbrains.annotations.NotNull
    public final com.example.shahicripto.model.local.NewsData.NewsData copy(@org.jetbrains.annotations.NotNull
    java.util.List<com.example.shahicripto.model.local.NewsData.NewsData.Data> data, boolean hasWarning, @org.jetbrains.annotations.NotNull
    java.lang.String message, @org.jetbrains.annotations.NotNull
    java.util.List<? extends java.lang.Object> promoted, @org.jetbrains.annotations.NotNull
    com.example.shahicripto.model.local.NewsData.NewsData.RateLimit rateLimit, int type) {
        return null;
    }
    
    @java.lang.Override
    public boolean equals(@org.jetbrains.annotations.Nullable
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override
    public int hashCode() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull
    @java.lang.Override
    public java.lang.String toString() {
        return null;
    }
    
    public NewsData(@org.jetbrains.annotations.NotNull
    java.util.List<com.example.shahicripto.model.local.NewsData.NewsData.Data> data, boolean hasWarning, @org.jetbrains.annotations.NotNull
    java.lang.String message, @org.jetbrains.annotations.NotNull
    java.util.List<? extends java.lang.Object> promoted, @org.jetbrains.annotations.NotNull
    com.example.shahicripto.model.local.NewsData.NewsData.RateLimit rateLimit, int type) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<com.example.shahicripto.model.local.NewsData.NewsData.Data> component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<com.example.shahicripto.model.local.NewsData.NewsData.Data> getData() {
        return null;
    }
    
    public final boolean component2() {
        return false;
    }
    
    public final boolean getHasWarning() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getMessage() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<java.lang.Object> component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<java.lang.Object> getPromoted() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.example.shahicripto.model.local.NewsData.NewsData.RateLimit component5() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.example.shahicripto.model.local.NewsData.NewsData.RateLimit getRateLimit() {
        return null;
    }
    
    public final int component6() {
        return 0;
    }
    
    public final int getType() {
        return 0;
    }
    
    @kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b&\n\u0002\u0010\u000b\n\u0002\b\u0005\b\u0086\b\u0018\u00002\u00020\u0001:\u00019Bu\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u0012\u0006\u0010\b\u001a\u00020\u0003\u0012\u0006\u0010\t\u001a\u00020\u0003\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\u0003\u0012\u0006\u0010\r\u001a\u00020\u000e\u0012\u0006\u0010\u000f\u001a\u00020\u0003\u0012\u0006\u0010\u0010\u001a\u00020\u0003\u0012\u0006\u0010\u0011\u001a\u00020\u0003\u0012\u0006\u0010\u0012\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0013J\t\u0010%\u001a\u00020\u0003H\u00c6\u0003J\t\u0010&\u001a\u00020\u000eH\u00c6\u0003J\t\u0010\'\u001a\u00020\u0003H\u00c6\u0003J\t\u0010(\u001a\u00020\u0003H\u00c6\u0003J\t\u0010)\u001a\u00020\u0003H\u00c6\u0003J\t\u0010*\u001a\u00020\u0003H\u00c6\u0003J\t\u0010+\u001a\u00020\u0003H\u00c6\u0003J\t\u0010,\u001a\u00020\u0003H\u00c6\u0003J\t\u0010-\u001a\u00020\u0003H\u00c6\u0003J\t\u0010.\u001a\u00020\u0003H\u00c6\u0003J\t\u0010/\u001a\u00020\u0003H\u00c6\u0003J\t\u00100\u001a\u00020\u0003H\u00c6\u0003J\t\u00101\u001a\u00020\u000bH\u00c6\u0003J\t\u00102\u001a\u00020\u0003H\u00c6\u0003J\u0095\u0001\u00103\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\u00032\b\b\u0002\u0010\b\u001a\u00020\u00032\b\b\u0002\u0010\t\u001a\u00020\u00032\b\b\u0002\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\f\u001a\u00020\u00032\b\b\u0002\u0010\r\u001a\u00020\u000e2\b\b\u0002\u0010\u000f\u001a\u00020\u00032\b\b\u0002\u0010\u0010\u001a\u00020\u00032\b\b\u0002\u0010\u0011\u001a\u00020\u00032\b\b\u0002\u0010\u0012\u001a\u00020\u0003H\u00c6\u0001J\u0013\u00104\u001a\u0002052\b\u00106\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u00107\u001a\u00020\u000bH\u00d6\u0001J\t\u00108\u001a\u00020\u0003H\u00d6\u0001R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0016\u0010\u0004\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0015R\u0016\u0010\u0005\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0015R\u0016\u0010\u0006\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0015R\u0016\u0010\u0007\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0015R\u0016\u0010\b\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0015R\u0016\u0010\t\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0015R\u0016\u0010\n\u001a\u00020\u000b8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR\u0016\u0010\f\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0015R\u0016\u0010\r\u001a\u00020\u000e8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 R\u0016\u0010\u000f\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\u0015R\u0016\u0010\u0010\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u0015R\u0016\u0010\u0011\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010\u0015R\u0016\u0010\u0012\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b$\u0010\u0015\u00a8\u0006:"}, d2 = {"Lcom/example/shahicripto/model/local/NewsData/NewsData$Data;", "", "body", "", "categories", "downvotes", "guid", "id", "imageurl", "lang", "publishedOn", "", "source", "sourceInfo", "Lcom/example/shahicripto/model/local/NewsData/NewsData$Data$SourceInfo;", "tags", "title", "upvotes", "url", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;Lcom/example/shahicripto/model/local/NewsData/NewsData$Data$SourceInfo;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getBody", "()Ljava/lang/String;", "getCategories", "getDownvotes", "getGuid", "getId", "getImageurl", "getLang", "getPublishedOn", "()I", "getSource", "getSourceInfo", "()Lcom/example/shahicripto/model/local/NewsData/NewsData$Data$SourceInfo;", "getTags", "getTitle", "getUpvotes", "getUrl", "component1", "component10", "component11", "component12", "component13", "component14", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "", "other", "hashCode", "toString", "SourceInfo", "app_debug"})
    public static final class Data {
        @org.jetbrains.annotations.NotNull
        @com.google.gson.annotations.SerializedName(value = "body")
        private final java.lang.String body = null;
        @org.jetbrains.annotations.NotNull
        @com.google.gson.annotations.SerializedName(value = "categories")
        private final java.lang.String categories = null;
        @org.jetbrains.annotations.NotNull
        @com.google.gson.annotations.SerializedName(value = "downvotes")
        private final java.lang.String downvotes = null;
        @org.jetbrains.annotations.NotNull
        @com.google.gson.annotations.SerializedName(value = "guid")
        private final java.lang.String guid = null;
        @org.jetbrains.annotations.NotNull
        @com.google.gson.annotations.SerializedName(value = "id")
        private final java.lang.String id = null;
        @org.jetbrains.annotations.NotNull
        @com.google.gson.annotations.SerializedName(value = "imageurl")
        private final java.lang.String imageurl = null;
        @org.jetbrains.annotations.NotNull
        @com.google.gson.annotations.SerializedName(value = "lang")
        private final java.lang.String lang = null;
        @com.google.gson.annotations.SerializedName(value = "published_on")
        private final int publishedOn = 0;
        @org.jetbrains.annotations.NotNull
        @com.google.gson.annotations.SerializedName(value = "source")
        private final java.lang.String source = null;
        @org.jetbrains.annotations.NotNull
        @com.google.gson.annotations.SerializedName(value = "source_info")
        private final com.example.shahicripto.model.local.NewsData.NewsData.Data.SourceInfo sourceInfo = null;
        @org.jetbrains.annotations.NotNull
        @com.google.gson.annotations.SerializedName(value = "tags")
        private final java.lang.String tags = null;
        @org.jetbrains.annotations.NotNull
        @com.google.gson.annotations.SerializedName(value = "title")
        private final java.lang.String title = null;
        @org.jetbrains.annotations.NotNull
        @com.google.gson.annotations.SerializedName(value = "upvotes")
        private final java.lang.String upvotes = null;
        @org.jetbrains.annotations.NotNull
        @com.google.gson.annotations.SerializedName(value = "url")
        private final java.lang.String url = null;
        
        @org.jetbrains.annotations.NotNull
        public final com.example.shahicripto.model.local.NewsData.NewsData.Data copy(@org.jetbrains.annotations.NotNull
        java.lang.String body, @org.jetbrains.annotations.NotNull
        java.lang.String categories, @org.jetbrains.annotations.NotNull
        java.lang.String downvotes, @org.jetbrains.annotations.NotNull
        java.lang.String guid, @org.jetbrains.annotations.NotNull
        java.lang.String id, @org.jetbrains.annotations.NotNull
        java.lang.String imageurl, @org.jetbrains.annotations.NotNull
        java.lang.String lang, int publishedOn, @org.jetbrains.annotations.NotNull
        java.lang.String source, @org.jetbrains.annotations.NotNull
        com.example.shahicripto.model.local.NewsData.NewsData.Data.SourceInfo sourceInfo, @org.jetbrains.annotations.NotNull
        java.lang.String tags, @org.jetbrains.annotations.NotNull
        java.lang.String title, @org.jetbrains.annotations.NotNull
        java.lang.String upvotes, @org.jetbrains.annotations.NotNull
        java.lang.String url) {
            return null;
        }
        
        @java.lang.Override
        public boolean equals(@org.jetbrains.annotations.Nullable
        java.lang.Object other) {
            return false;
        }
        
        @java.lang.Override
        public int hashCode() {
            return 0;
        }
        
        @org.jetbrains.annotations.NotNull
        @java.lang.Override
        public java.lang.String toString() {
            return null;
        }
        
        public Data(@org.jetbrains.annotations.NotNull
        java.lang.String body, @org.jetbrains.annotations.NotNull
        java.lang.String categories, @org.jetbrains.annotations.NotNull
        java.lang.String downvotes, @org.jetbrains.annotations.NotNull
        java.lang.String guid, @org.jetbrains.annotations.NotNull
        java.lang.String id, @org.jetbrains.annotations.NotNull
        java.lang.String imageurl, @org.jetbrains.annotations.NotNull
        java.lang.String lang, int publishedOn, @org.jetbrains.annotations.NotNull
        java.lang.String source, @org.jetbrains.annotations.NotNull
        com.example.shahicripto.model.local.NewsData.NewsData.Data.SourceInfo sourceInfo, @org.jetbrains.annotations.NotNull
        java.lang.String tags, @org.jetbrains.annotations.NotNull
        java.lang.String title, @org.jetbrains.annotations.NotNull
        java.lang.String upvotes, @org.jetbrains.annotations.NotNull
        java.lang.String url) {
            super();
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String component1() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String getBody() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String component2() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String getCategories() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String component3() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String getDownvotes() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String component4() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String getGuid() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String component5() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String getId() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String component6() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String getImageurl() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String component7() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String getLang() {
            return null;
        }
        
        public final int component8() {
            return 0;
        }
        
        public final int getPublishedOn() {
            return 0;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String component9() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String getSource() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.example.shahicripto.model.local.NewsData.NewsData.Data.SourceInfo component10() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.example.shahicripto.model.local.NewsData.NewsData.Data.SourceInfo getSourceInfo() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String component11() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String getTags() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String component12() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String getTitle() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String component13() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String getUpvotes() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String component14() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String getUrl() {
            return null;
        }
        
        @kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\f\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\r\u001a\u00020\u0003H\u00c6\u0003J\'\u0010\u000e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0012\u001a\u00020\u0013H\u00d6\u0001J\t\u0010\u0014\u001a\u00020\u0003H\u00d6\u0001R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0016\u0010\u0004\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\bR\u0016\u0010\u0005\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\b\u00a8\u0006\u0015"}, d2 = {"Lcom/example/shahicripto/model/local/NewsData/NewsData$Data$SourceInfo;", "", "img", "", "lang", "name", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getImg", "()Ljava/lang/String;", "getLang", "getName", "component1", "component2", "component3", "copy", "equals", "", "other", "hashCode", "", "toString", "app_debug"})
        public static final class SourceInfo {
            @org.jetbrains.annotations.NotNull
            @com.google.gson.annotations.SerializedName(value = "img")
            private final java.lang.String img = null;
            @org.jetbrains.annotations.NotNull
            @com.google.gson.annotations.SerializedName(value = "lang")
            private final java.lang.String lang = null;
            @org.jetbrains.annotations.NotNull
            @com.google.gson.annotations.SerializedName(value = "name")
            private final java.lang.String name = null;
            
            @org.jetbrains.annotations.NotNull
            public final com.example.shahicripto.model.local.NewsData.NewsData.Data.SourceInfo copy(@org.jetbrains.annotations.NotNull
            java.lang.String img, @org.jetbrains.annotations.NotNull
            java.lang.String lang, @org.jetbrains.annotations.NotNull
            java.lang.String name) {
                return null;
            }
            
            @java.lang.Override
            public boolean equals(@org.jetbrains.annotations.Nullable
            java.lang.Object other) {
                return false;
            }
            
            @java.lang.Override
            public int hashCode() {
                return 0;
            }
            
            @org.jetbrains.annotations.NotNull
            @java.lang.Override
            public java.lang.String toString() {
                return null;
            }
            
            public SourceInfo(@org.jetbrains.annotations.NotNull
            java.lang.String img, @org.jetbrains.annotations.NotNull
            java.lang.String lang, @org.jetbrains.annotations.NotNull
            java.lang.String name) {
                super();
            }
            
            @org.jetbrains.annotations.NotNull
            public final java.lang.String component1() {
                return null;
            }
            
            @org.jetbrains.annotations.NotNull
            public final java.lang.String getImg() {
                return null;
            }
            
            @org.jetbrains.annotations.NotNull
            public final java.lang.String component2() {
                return null;
            }
            
            @org.jetbrains.annotations.NotNull
            public final java.lang.String getLang() {
                return null;
            }
            
            @org.jetbrains.annotations.NotNull
            public final java.lang.String component3() {
                return null;
            }
            
            @org.jetbrains.annotations.NotNull
            public final java.lang.String getName() {
                return null;
            }
        }
    }
    
    @kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/example/shahicripto/model/local/NewsData/NewsData$RateLimit;", "", "()V", "app_debug"})
    public static final class RateLimit {
        
        public RateLimit() {
            super();
        }
    }
}