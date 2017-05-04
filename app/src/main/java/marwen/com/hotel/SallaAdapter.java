package marwen.com.hotel;

/**
 * Created by Taher on 25/04/2016.
 */
import java.io.InputStream;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class SallaAdapter extends ArrayAdapter<SalleReunion> {
    public final static String TAG = "salle";
    private int resourceId = 0;
    private LayoutInflater inflater;
    InputStream is = null;
    String result = null;
    String line = null;

    List<SalleReunion> list;
    AlertDialogManager alert = new AlertDialogManager();

    public SallaAdapter(Context context, int resourceId,
                                List<SalleReunion> salles) {
        super(context, 0, salles);
        this.list = salles;
        this.resourceId = resourceId;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        TextView txtTitle;
        View rowView;
        String imageView;
        ImageView image;


        rowView = inflater.inflate(resourceId, parent, false);

        try {
         //   txtTitle = (TextView) rowView.findViewById(R.id.firstLine);

            image = (ImageView) rowView.findViewById(R.id.imageDownloaded);

        } catch (ClassCastException e) {
            throw e;
        }
        final SalleReunion item = getItem(position);
        //txtTitle.setText(item.getNom());
        imageView=item.getImage().toString();
        byte[] decodedString = Base64.decode(imageView, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        image.setImageBitmap(decodedByte);

        // image.setImageBitmap(bmp);
        return rowView;

    };
}