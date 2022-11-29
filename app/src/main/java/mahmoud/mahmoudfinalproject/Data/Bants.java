package mahmoud.mahmoudfinalproject.Data;

import android.net.Uri;
import android.widget.ImageView;

/**
 * فىه تصف مهمه بادارت المهمات
 */
public class Bants
{
    /**
     * رقم مميز يتم النتاجه من قبل الخادم
     */
    private String Key;
    private String Event;
    private String Date;
    private String Color;
    private String Owner;
    private  int Important;
    private ImageView Image;

    public Bants()
    {

    }

    //get
    public int getImportant()
    {
        return Important;
    }

    public ImageView getImage() {return Image;}

    public String getKey()
    {
        return Key;
    }

    public String getEvent()
    {
        return Event;
    }

    public String getDate()
    {
        return Date;
    }

    public String getColor()
    {
        return Color;
    }

    public String getOwner()
    {
        return Owner;
    }

    //set
    public void setKey(String key)
    {
        Key = key;
    }

    public void setImage(ImageView image){Image = image;}

    public void setImportant(int important)
    {
        Important = important;
    }

    public void setEvent(String event)
    {
        Event = event;
    }

    public void setDate(String date)
    {
        Date = date;
    }


    public void setColor(String color)
    {
        Color = color;
    }

    public void setOwner(String owner)
    {
        Owner = owner;
    }

    @Override
    public String toString() {
        return "Bants{" +
                "Key='" + Key + '\'' +
                ", Event='" + Event + '\'' +
                ", Date='" + Date + '\'' +
                ", Color='" + Color + '\'' +
                ", Owner='" + Owner + '\'' +
                ", Important='" + Important + '\'' +
                ", Image='" + Image + '\'' +
                '}';
    }
}