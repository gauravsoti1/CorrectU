package androidavengers.correctu.utility;

/**
 * Created by hp on 1/28/2016.
 */
public class WordPojo {

    private String Word;

    //private String[] Forms;

    private String[] Definitions;

    private String PartOfSpeech;

    public String getWord() {
        return Word;
    }

    public void setWord(String Word) {
        this.Word = Word;
    }

    /*public String[] getForms() {
        return Forms;
    }

    public void setForms(String[] Forms) {
        this.Forms = Forms;
    }
*/
    public String[] getDefinitions() {
        return Definitions;
    }

    public void setDefinitions(String[] Definitions) {
        this.Definitions = Definitions;
    }

    public String getPartOfSpeech() {
        return PartOfSpeech;
    }

    public void setPartOfSpeech(String PartOfSpeech) {
        this.PartOfSpeech = PartOfSpeech;
    }

    @Override
    public String toString() {
        return "ClassPojo [Word = " + Word + /*", Forms = " + Forms +*/ ", Definitions = " + Definitions + ", PartOfSpeech = " + PartOfSpeech + "]";
    }
}
