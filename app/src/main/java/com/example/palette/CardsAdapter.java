    package com.example.palette;

    import android.app.Activity;
    import android.content.Context;
    import android.content.Intent;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.ImageView;
    import android.widget.Toast;

    import androidx.core.app.ActivityOptionsCompat;
    import androidx.recyclerview.widget.RecyclerView;

    import java.util.ArrayList;

    public class CardsAdapter extends RecyclerView.Adapter<CardsAdapter.TarjViewHolder> {

        private ArrayList<Tarjeta> items;
        private View.OnClickListener onClickListener;

        public CardsAdapter(ArrayList<Tarjeta> items) {
            this.items = items;
        }

        public void setOnClick(View.OnClickListener onClickListener) {
            this.onClickListener = onClickListener;
        }

        public static class TarjViewHolder extends RecyclerView.ViewHolder {
            ImageView imagen;

            public TarjViewHolder(View itemView) {
                super(itemView);
                imagen = itemView.findViewById(R.id.image1);
            }

        }

        @Override
        public TarjViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_cards, viewGroup, false);
            return new TarjViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(TarjViewHolder viewHolder, int pos) {
            Tarjeta item = items.get(pos);
            Context context = viewHolder.itemView.getContext();

            viewHolder.imagen.setImageResource(item.getImagen());

            // Configura el nombre de transición para la animación compartida
            String transitionName = context.getString(R.string.nombre_transicion_imagen);
            viewHolder.imagen.setTransitionName(transitionName);

            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Iniciar la actividad ImagePalette con la información necesaria
                    Intent intent = new Intent(context, ImagePalette.class);
                    intent.putExtra("image_resource", item.getImagen());  // Pasa la información necesaria a ImagePalette

                    // Configura las opciones para la animación de transición
                    ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                            (Activity) context,
                            viewHolder.imagen,
                            transitionName);

                    // Inicia la actividad con las opciones de transición
                    context.startActivity(intent, options.toBundle());

                    // Aplica la transición de animación después de iniciar la actividad
                    ((Activity) context).overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }
            });
        }



        @Override
        public int getItemCount() {
            return items.size();
        }
    }
