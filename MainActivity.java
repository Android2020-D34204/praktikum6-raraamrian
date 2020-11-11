package id.ac.id.telkomuniversity.tass.praktikactivity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button buttonPindah;
    EditText editText;
    String katakata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonPindah = findViewById(R.id.buttonPindah);
        editText = findViewById(R.id.EditText);

        buttonPindah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                katakata = editText.getText().toString();
                if (katakata.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Input tidak boleh kosong!", Toast.LENGTH_SHORT).show();
                } else {
                    alert();
                }
            }
        });
    }

    public void alert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Anda yakin ingin pindah?");
        builder.setPositiveButton("ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String katakata = editText.getText().toString();
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                intent.putExtra("editText", katakata);
                notifikasi();
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Tidak", null);
        builder.show();
    }

    public void notifikasi(){
        int NOTIF_ID = 18;
        String CHANNEL_ID = "notif";
        Intent intent = new Intent(this, SecondActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = CHANNEL_ID;
            String textDeskripsi = CHANNEL_ID;
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(textDeskripsi);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID);
        builder.setSmallIcon(R.drawable.ic_launcher_background);
        builder.setContentTitle("Praktik Activity");
        builder.setContentText("Berhasil pindah ke activity kedua");
        builder.setStyle(new NotificationCompat.BigTextStyle().bigText("Berhasil pindah ke activity kedua"));
        builder.setPriority((NotificationCompat.PRIORITY_DEFAULT));
        builder.setContentIntent(pendingIntent);
        NotificationManagerCompat noticationManager = NotificationManagerCompat.from(this);
        noticationManager.notify(NOTIF_ID, builder.build());
    }
}