package domein;

import java.util.ArrayList;
import java.util.List;

public class EdelsteenRepository {
    private List<Edelsteenfiche> edelstenen;

    public EdelsteenRepository() {
        edelstenen = new ArrayList<>();
    }

    public void addEdelsteen(Edelsteenfiche edelsteen) {
        edelstenen.add(edelsteen);
    }

    public List<Edelsteenfiche> getEdelstenen() {
        return edelstenen;
    }
}

