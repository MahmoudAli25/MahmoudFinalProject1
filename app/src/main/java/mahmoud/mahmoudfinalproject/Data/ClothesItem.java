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
    private String Title;
    private String Date;
    private String Type;
    private String Color;
    private String Owner;

    public ClothesItem()
    {

    }

    //get
    public String getKey()
    {
        return Key;
    }

    public String getTitle()
    {
        return Title;
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

    public void setTitle(String title)
    {
        Title = title;
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
                ", Title='" + Title + '\'' +
                ", Date='" + Date + '\'' +
                ", Type='" + Type + '\'' +
                ", Color='" + Color + '\'' +
                ", Owner='" + Owner + '\'' +
                '}';
    }
}
