package ucf.chickenzombiebonanza;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.Toast;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

public class MyItemizedOverlay extends ItemizedOverlay<OverlayItem> {
        
    private ArrayList<OverlayItem> myOverlays ;
	protected int[] addOverlayenemy;
	private Context context;

    public MyItemizedOverlay(Drawable defaultMarker) {
        super(boundCenterBottom(defaultMarker));
        myOverlays = new ArrayList<OverlayItem>();
        populate();
    }
        
    public void addOverlay(OverlayItem overlay){
        myOverlays.add(overlay);
        populate();
    }

    @Override
    protected OverlayItem createItem(int i) {
        return myOverlays.get(i);
    }
        
    // Removes overlay item i
    public void removeItem(int i){
        myOverlays.remove(i);
        populate();
    }
        
    

    // Returns present number of items in list
    @Override
    public int size() {
        return myOverlays.size();
    }

	
}