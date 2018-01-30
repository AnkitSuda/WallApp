package example.androidgrid.simplewallpapersapp.ui;

import android.app.DownloadManager;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import example.androidgrid.simplewallpapersapp.R;
import example.androidgrid.simplewallpapersapp.model.Category;
import example.androidgrid.simplewallpapersapp.util.NavigationUtil;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.list)
    RecyclerView list;
    LinearLayoutManager linearLayoutManager;
    FirebaseRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setupList();
    }

    private void setupList() {
        linearLayoutManager = new LinearLayoutManager(this);

        list.setLayoutManager(linearLayoutManager);
        list.setHasFixedSize(true);

        fetch();
    }

    private void fetch() {
        Query query = FirebaseDatabase.getInstance()
                .getReference().child("categories");

        FirebaseRecyclerOptions<Category> options = new FirebaseRecyclerOptions.Builder<Category>()
                .setQuery(query, new SnapshotParser<Category>() {
                    @NonNull
                    @Override
                    public Category parseSnapshot(@NonNull DataSnapshot snapshot) {
                        return new Category(snapshot.child("id").getValue().toString(),
                                snapshot.child("thumb").getValue().toString(),
                                snapshot.child("title").getValue().toString());
                    }
                }).build();

        adapter = new FirebaseRecyclerAdapter<Category, ViewHolder>(options) {
            @Override
            public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_category_layout, parent, false);
                return new ViewHolder(v);
            }

            @Override
            protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Category model) {
                holder.textMain.setText(model.getCatTitle());

                Picasso.with(getApplicationContext())
                        .load(model.getCatImage())
                        .into(holder.img);

                holder.mainBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(MainActivity.this, String.valueOf(position), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };

        list.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.add_cat:
                NavigationUtil.goToAddCat(this);
                break;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.cat_main_card)
        CardView mainBtn;
        @BindView(R.id.cat_main_text)
        TextView textMain;
        @BindView(R.id.cat_main_bg)
        AppCompatImageView img;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
