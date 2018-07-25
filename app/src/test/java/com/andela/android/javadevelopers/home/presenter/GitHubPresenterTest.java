package com.andela.android.javadevelopers.home.presenter;

import com.andela.android.javadevelopers.home.api.GitHubApi;
import com.andela.android.javadevelopers.home.contract.HomeContract.GetDevelopersIntractor;
import com.andela.android.javadevelopers.home.contract.HomeContract.GetDevelopersIntractor.OnFinishedListener;
import com.andela.android.javadevelopers.home.contract.HomeContract.HomePresenter;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static com.andela.android.javadevelopers.home.contract.HomeContract.HomeView;


/**
 * The type Git hub presenter test.
 */
@RunWith(MockitoJUnitRunner.class)
public class GitHubPresenterTest {

    @Mock private OnFinishedListener onFinishedListener;
    @Mock private GetDevelopersIntractor intractor;
    @Mock private HomeView view;
    @Mock private GitHubApi gitHubApi;

    private String url;

    private HomePresenter presenter;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        presenter = new GitHubPresenter(intractor);

        presenter.setView(view);

        url = "search/users?q=language:java+location:"
                + "lagos" + "&per_page=" + 10 + "&sort=followers";
    }

    @Test
    public void setView() throws Exception {
        Assert.assertNotNull(view);
    }

    @Test
    public void requestDataFromServer() throws Exception {
        presenter.requestDataFromServer(gitHubApi, "lagos", "10");

        Mockito.verify(view).showLoader();

        Mockito.verify(intractor)
                .getDevelopersArrayList(gitHubApi, url, (OnFinishedListener) presenter);
    }
}
