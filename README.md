# RxJavaBackgroundCallAdapterFactory
The goal is compatible behavior with version 1.x RxAdapter.

# usage
        new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaBackgroundCallAdapterFactory.create())
