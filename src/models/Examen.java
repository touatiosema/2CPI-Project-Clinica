package models;

public class Examen implements Comparable<Examen>{
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String type;
    private String name;
    public Examen(String type, String name){
        this.type=type;
        this.name=name;
    }
    public void afficher(){
        System.out.println("type: "+type+"name: "+name);
    }


    @Override
    public int compareTo(Examen o) {
        return  type.compareTo(o.getType());
    }
}
