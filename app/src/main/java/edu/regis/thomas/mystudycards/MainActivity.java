package edu.regis.thomas.mystudycards;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import edu.regis.thomas.mystudycards.domain.Deck;
import edu.regis.thomas.mystudycards.service.BackupService;
import edu.regis.thomas.mystudycards.service.DeckSvcSQLiteImpl;
import edu.regis.thomas.mystudycards.service.IDeckSvc;

/**
 * Class retrieves list of Deck objects from SQLite database to populate ListView.
 * Serves as Main activity view and entry point into app functionality.
 *
 * @author Nathan Thomas
 * @version 1.0
 */
public class MainActivity extends AppCompatActivity {
    private ListView listView = null;
    private IDeckSvc deckSvc = null;
    private ArrayAdapter adapter = null;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.deckList);
        //deckSvc = DeckSvcCacheImpl.getInstance();
        deckSvc = DeckSvcSQLiteImpl.getInstance(getApplicationContext());

        // Broadcast Receiver
        br = new MyBroadcastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("edu.regis.thomas.mystudycards.EXPORT_DATABASE");
        this.registerReceiver(br, filter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu within the action bar
        getMenuInflater().inflate(R.menu.appbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.action_newdeck:
                Log.i(TAG, "action New Deck clicked!");
                intent = new Intent(this, AddDeckActivity.class);
                startActivity(intent);
                break;
            case R.id.action_references:
                Log.i(TAG, "action References clicked!");
                intent = new Intent(this, ReferenceListActivity.class);
                startActivity(intent);
                break;
            case R.id.action_contactus:
                Log.i(TAG, "action Contact Us clicked!");
                intent = new Intent(this, ContactUsActivity.class);
                startActivity(intent);
                break;
            case R.id.action_about:
                Log.i(TAG, "action About clicked!");
                intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
                break;
            case R.id.action_comingsoon:
                Log.i(TAG, "action Coming Soon clicked!");
                intent = new Intent(this, ComingSoonActivity.class);
                startActivity(intent);
                break;
            case R.id.action_backup:
                Log.i(TAG, "action Backup clicked!");
                intent = new Intent(this, BackupService.class);
                startService(intent);
                break;
            default:
                super.onOptionsItemSelected(item);
                break;
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        final List<Deck> deck = deckSvc.retrieveAll();
        adapter = new ArrayAdapter<Deck>(this,
                android.R.layout.simple_list_item_1, deck);
        listView.setAdapter(adapter);

        adapter.notifyDataSetChanged();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this,
                        ManageDeckActivity.class);
                intent.putExtra("Deck", deck.get(position));
                startActivity(intent);
            }
        });
    }

    MyBroadcastReceiver br;

    public class MyBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context, intent.getStringExtra("message"),
                    Toast.LENGTH_LONG).show();
        }
    }
}
