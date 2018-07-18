package com.andela.android.javadevelopers.dagger.module;

import com.andela.android.javadevelopers.dagger.scope.ActivityScope;
import com.andela.android.javadevelopers.home.adapter.GitHubUsersAdapter;
import com.andela.android.javadevelopers.home.contract.HomeContract.RecyclerItemClickListener;
import com.andela.android.javadevelopers.home.view.MainActivity;

import dagger.Module;
import dagger.Provides;


/**
 * The type Git hub adapter module.
 */
@Module(includes = MainActivityContextModule.class)
public class GitHubAdapterModule {
    /**
     * Gets star wars people l ist.
     *
     * @param itemClickListener the item click listener
     * @return the star wars people l ist
     */
    @Provides
    @ActivityScope
    GitHubUsersAdapter getGitHubUsers(RecyclerItemClickListener itemClickListener) {
        return new GitHubUsersAdapter(itemClickListener);
    }

    /**a
     * Gets item click listener.
     *
     * @param mainActivity the main activity
     * @return the item click listener
     */
    @Provides
    @ActivityScope
    RecyclerItemClickListener getItemClickListener(MainActivity mainActivity) {
        return mainActivity;
    }

}
