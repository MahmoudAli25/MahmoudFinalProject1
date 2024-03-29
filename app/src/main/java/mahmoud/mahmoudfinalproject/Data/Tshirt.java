package mahmoud.mahmoudfinalproject.Data;

import android.widget.ImageView;

/**
 * فىه تصف مهمه بادارت المهمات
 */
public class Tshirt
{
    /**
     * رقم مميز يتم النتاجه من قبل الخادم
     */
    private String Key;
    private String Event;
    long times;
    private String Color;
    private String Owner;
    private  int Important;
    private String Image;

    public Tshirt()
    {

    }

    //get
    public int getImportant()
    {
        return Important;
    }

    public String getKey()
    {
        return Key;
    }

    public String getEvent()
    {
        return Event;
    }

    public long getTimes() {
        return times;
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


    public void setImportant(int important)
    {
        Important = important;
    }

    public void setEvent(String event)
    {
        Event = event;
    }

    public void setTimes(long times) {
        this.times = times;
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
        return "Tshirt{" +
                "Key='" + Key + '\'' +
                ", Event='" + Event + '\'' +
                ", Date='" + times + '\'' +
                ", Color='" + Color + '\'' +
                ", Owner='" + Owner + '\'' +
                ", Important='" + Important + '\'' +
                ", Image='" + Image + '\'' +
                '}';
    }

    public void setImage(String image) {
        this.Image = image;
    }

    public String getImage() {
        return Image;
    }
}

