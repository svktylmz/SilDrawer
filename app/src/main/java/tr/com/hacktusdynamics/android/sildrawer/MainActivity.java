package tr.com.hacktusdynamics.android.sildrawer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileSettingDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int PROFILE_SETTING = 1; //identifier 1

    private AccountHeader headerAccount = null;
    private Drawer result = null;

    private IProfile profileGuest;
    private IProfile profileTwo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Handle toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Main");

        //create guest profileGuest
        profileGuest = new ProfileDrawerItem().withName("Guest").withEmail("guest@gmail.com").withIcon(getResources().getDrawable(R.drawable.profileGuest));
        profileTwo = new ProfileDrawerItem().withName("Two").withEmail("two@gmail.com").withIcon(getResources().getDrawable(R.drawable.profileTwo)).withIdentifier(2);

        // Create the AccountHeader
        buildHeader(false, savedInstanceState);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * small helper method to reuse the logic to build the AccountHeader
     * this will be used to replace the headerAccount of the drawer with a compact/normal headerAccount
     *
     * @param compact
     * @param savedInstanceState
     */
    private void buildHeader(boolean compact, Bundle savedInstanceState) {
        //Create the AccountHeader
        headerAccount = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.header)
                .withCompactStyle(compact)
                .addProfiles(
                        profileGuest,
                        profileTwo,
                        //
                        new ProfileSettingDrawerItem().withName("Add Account")
                                .withDescription("Add new GitHub Account")
                                .withIcon(android.R.drawable.ic_btn_speak_now)
                                .withIdentifier(PROFILE_SETTING),//.withIcon(new IconicsDrawable(this, GoogleMaterial.Icon.gmd_plus).actionBar().paddingDp(5).colorRes(R.color.material_drawer_dark_primary_text)).withIdentifier(PROFILE_SETTING),
                        new ProfileSettingDrawerItem().withName("Manage Account")
                                .withIcon(android.R.drawable.ic_dialog_info)
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean current) {
                        //if the clicked item has the identifier 1, add a new profile
                        if (profile instanceof IDrawerItem && ((IDrawerItem) profile).getIdentifier() == PROFILE_SETTING) {
                            IProfile newProfile = new ProfileDrawerItem()
                                    .withNameShown(true)
                                    .withName("NEW PROFILE")
                                    .withEmail("newprofile@gmail.com")
                                    .withIcon(getResources().getDrawable(R.drawable.profileNew));
                            if (headerAccount.getProfiles() != null) {
                                //we know that there are 2 setting elements. set the new profile above them
                            }
                        }


                        //false if you have not consumed the event and it should close the drawer
                        return false;
                    }
                })
                .withSavedInstance(savedInstanceState)
                .build();
    }

}
