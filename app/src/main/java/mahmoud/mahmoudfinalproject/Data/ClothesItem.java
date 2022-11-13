package mahmoud.mahmoudfinalproject.Data;
/**
 * فىه تصف مهمه بادارت المهمات
 */
public class ClothesItem
{
    /**
     * رقم مميز يتم النتاجه من قبل الخادم
     */
    private String Key;
    private String Event;
    private String Date;
    private String Type;
    private String Color;
    private String Owner;
    private  int Important;

    public ClothesItem()
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

    public String getDate()
    {
        return Date;
    }

    public String getType()
    {
        return Type;
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

    public void setTitle(String title)
    {
        Event = Event;
    }

    public void setDate(String date)
    {
        Date = date;
    }

    public void setType(String type)
    {
        Type = type;
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
        return "ClothesItem{" +
                "Key='" + Key + '\'' +
                ", Event='" + Event + '\'' +
                ", Date='" + Date + '\'' +
                ", Type='" + Type + '\'' +
                ", Color='" + Color + '\'' +
                ", Owner='" + Owner + '\'' +
                ", Important='" + Important + '\'' +

                '}';
    }
}
