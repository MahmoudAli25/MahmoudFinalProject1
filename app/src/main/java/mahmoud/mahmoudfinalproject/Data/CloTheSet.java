package mahmoud.mahmoudfinalproject.Data;

public class CloTheSet
{
    private Tshirt t;
    private Bants b;

    private String Key;
    private String Owner;


    public String getKey() {
        return Key;
    }

    public String getOwner() {
        return Owner;
    }

    public Tshirt getT() {
        return t;
    }

    public void setT(Tshirt t) {
        this.t = t;
    }

    public Bants getB() {
        return b;
    }

    public void setB(Bants b) {
        this.b = b;
    }

    public void setKey(String key) {
        Key = key;
    }

    public void setOwner(String owner) {
        Owner = owner;
    }
}
