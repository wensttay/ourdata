package br.ifpb.simba.ourdata.core.temporal.heideltime;

/**
 * This class is a result type for
 * {@link br.ifpb.simba.ourdata.core.temporal.heideltime.HeidelTimeEngine}.
 *
 * @author Wensttay de Sousa Alencar <yattsnew@gmail.com>
 * @date 28/03/2017, 21:32:44
 */
public class HeidelTimeToken {

    private String id;
    private String type;
    private String value;
    private String data;

    public HeidelTimeToken() {
    }

    public HeidelTimeToken(String id, String type, String value, String data) {
        this.id = id;
        this.type = type;
        this.value = value;
        this.data = data;
    }

    @Override
    public String toString() {
        return "HeidelTimeToken{" + "id=" + id + ", type=" + type + ", value=" + value + ", data=" + data + '}';
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * @return the data
     */
    public String getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(String data) {
        this.data = data;
    }

}
