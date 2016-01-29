#### 英語があやしいので日本語のmdも用意していくスタイル

# RxJavaBackgroundCallAdapterFactory
目標 は、1系と同じ動作をするアダプタの作成

# 使い方
        new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaBackgroundCallAdapterFactory.create())

