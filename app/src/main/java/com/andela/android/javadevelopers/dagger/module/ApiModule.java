package com.andela.android.javadevelopers.dagger.module;

import com.andela.android.javadevelopers.dagger.scope.ActivityScope;
import com.andela.android.javadevelopers.home.api.GitHubApi;
import com.andela.android.javadevelopers.home.model.GetDevelopersIntractorImpl;
import com.andela.android.javadevelopers.home.presenter.GitHubPresenter;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.andela.android.javadevelopers.home.contract.HomeContract.GetDevelopersIntractor;
import static com.andela.android.javadevelopers.home.contract.HomeContract.HomePresenter;


/**
 * The type Main activity module.
 */
@Module
public class ApiModule {
    private final String mBaseUrl;

    public ApiModule(String mBaseUrl) {
        this.mBaseUrl = mBaseUrl;
    }

    /**
     * Provide main activity presenter home presenter.
     *
     * @param getDevelopersIntractor the get developers intractor
     * @return the home presenter
     */
    @ActivityScope
    @Provides
    HomePresenter provideGitHubPresenter(GetDevelopersIntractor getDevelopersIntractor) {
        return new GitHubPresenter(getDevelopersIntractor);
    }

    /**
     * Provide intractor model get developers intractor.
     *
     * @return the get developers intractor
     */
    @ActivityScope
    @Provides
    GetDevelopersIntractor provideIntractorModel() {
        return new GetDevelopersIntractorImpl();
    }

    /**
     * Provide gson converter factory gson converter factory.
     *
     * @return the gson converter factory
     */
    @ActivityScope
    @Provides
    GsonConverterFactory provideGsonConverterFactory() {
        return GsonConverterFactory.create();
    }

    /**
     * Provide http logging interceptor http logging interceptor.
     *
     * @return the http logging interceptor
     */
    @ActivityScope
    @Provides
    HttpLoggingInterceptor provideHttpLoggingInterceptor() {
        HttpLoggingInterceptor logger = new HttpLoggingInterceptor();
        return logger.setLevel(HttpLoggingInterceptor.Level.HEADERS);
    }

    /**
     * Provide ok http client ok http client.
     *
     * @param loggingInterceptor the logging interceptor
     * @return the ok http client
     */
    @ActivityScope
    @Provides
    OkHttpClient provideOkHttpClient(HttpLoggingInterceptor loggingInterceptor) {
        return new OkHttpClient
                .Builder()
                .addInterceptor(loggingInterceptor)
                .connectTimeout(20, TimeUnit.SECONDS)
                .build();
    }

    /**
     * Provide retrofit retrofit.
     *
     * @param okHttpClient         the ok http client
     * @param gsonConverterFactory the gson converter factory
     * @return the retrofit
     */
    @ActivityScope
    @Provides
    Retrofit provideRetrofit(OkHttpClient okHttpClient, GsonConverterFactory gsonConverterFactory) {
        return new Retrofit.Builder()
                .baseUrl(mBaseUrl)
                .client(okHttpClient)
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @ActivityScope
    @Provides
    GitHubApi provideGitHubApi() {
        return provideRetrofit(provideOkHttpClient(provideHttpLoggingInterceptor()),
                provideGsonConverterFactory())
                .create(GitHubApi.class);
    }
}
