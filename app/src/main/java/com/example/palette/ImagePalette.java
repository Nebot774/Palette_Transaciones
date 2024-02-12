package com.example.palette;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.palette.graphics.Palette;

public class ImagePalette extends AppCompatActivity {


    // Definimos variables para cada TextView
    private TextView lightVibrantTextView;
    private TextView mutedTextView;
    private TextView darkMutedTextView;
    private TextView lightMutedTextView;

    @Override
    public void finish() {
        super.finish();
        // Aplica la animación de 'fade in' para la actividad que entra y 'slide out' para esta actividad que sale
        overridePendingTransition(R.anim.fade_in, R.anim.slide_out);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Solicita la característica de transiciones de contenido antes de llamar a setContentView
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);

        // Configura la animación de entrada Fade
        //Fade fade = new Fade();
        Explode explode= new Explode();
        explode.setDuration(1000); // Duración de la animación en milisegundos
        getWindow().setEnterTransition(explode);

        setContentView(R.layout.activity_image_palette);

        // Obtén la imagen seleccionada del Intent
        int selectedImage = getIntent().getIntExtra("image_resource", 0);

        // Configura la imagen en el ImageView
        ImageView imageView = findViewById(R.id.imageView);
        imageView.setImageResource(selectedImage);

        // Configura el nombre de transición para la imagen
        String transitionName = getIntent().getStringExtra("transition_name");
        imageView.setTransitionName(transitionName);

        // Configura la transición de elementos compartidos
        Transition transition = TransitionInflater.from(this)
                .inflateTransition(R.transition.change_image_transform);
        getWindow().setSharedElementEnterTransition(transition);

        // Inicializa las variables de los TextViews
        TextView lightVibrantTextView = findViewById(R.id.lightVibrantTextView);
        TextView mutedTextView = findViewById(R.id.mutedTextView);
        TextView darkMutedTextView = findViewById(R.id.darkMutedTextView);
        TextView lightMutedTextView = findViewById(R.id.lightMutedTextView);

        // Crea un objeto Bitmap de la imagen
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), selectedImage);

        // Usar Palette para extraer colores de la imagen
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(@NonNull Palette palette) {
                // Extrae los colores necesarios de la imagen
                int vibrantColor = palette.getVibrantColor(0x000000); // Color por defecto en caso de no encontrar
                int darkVibrantColor = palette.getDarkVibrantColor(0x000000);
                int lightVibrantColor = palette.getLightVibrantColor(0x000000);
                int mutedColor = palette.getMutedColor(0x000000);
                int darkMutedColor = palette.getDarkMutedColor(0x000000);
                int lightMutedColor = palette.getLightMutedColor(0x000000);

                // Aplica el color vibrant a la Toolbar
                Toolbar toolbar = findViewById(R.id.toolbar);
                toolbar.setBackgroundColor(vibrantColor);

                // Aplica el color dark vibrant a la StatusBar
                Window window = getWindow();
                window.setStatusBarColor(darkVibrantColor);

                // Aplica los otros colores a diferentes TextViews
                TextView lightVibrantTextView = findViewById(R.id.lightVibrantTextView);
                lightVibrantTextView.setBackgroundColor(lightVibrantColor);

                TextView mutedTextView = findViewById(R.id.mutedTextView);
                mutedTextView.setBackgroundColor(mutedColor);

                TextView darkMutedTextView = findViewById(R.id.darkMutedTextView);
                darkMutedTextView.setBackgroundColor(darkMutedColor);

                TextView lightMutedTextView = findViewById(R.id.lightMutedTextView);
                lightMutedTextView.setBackgroundColor(lightMutedColor);


            }
        });

    }
}
