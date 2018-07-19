package com.andela.android.javadevelopers.home.model;

import com.andela.android.javadevelopers.home.api.GitHubApi;
import com.andela.android.javadevelopers.home.contract.HomeContract;

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


/**
 * The type Get developers intractor.
 */
public class GetDevelopersIntractorImpl implements HomeContract.GetDevelopersIntractor {

    private ArrayList<GitHubUser> gitHubUser;

    @Override
    public void getDevelopersArrayList(GitHubApi gitHubApi,
                                       String url,
                                       final OnFinishedListener onFinishedListener) {

        gitHubApi.getDevelopersLists(url).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GitHubUsersResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        // letf blank intentionally
                    }

                    @Override
                    public void onNext(GitHubUsersResponse gitHubUsersResponse) {

                        assert gitHubUsersResponse != null;
                        gitHubUser = gitHubUsersResponse.getGitHubUsers();
                    }

                    @Override
                    public void onError(Throwable e) {
                        onFinishedListener.onFailure(e);
                    }

                    @Override
                    public void onComplete() {
                        onFinishedListener.onFinished(gitHubUser);
                    }
                });
    }
}
