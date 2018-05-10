package system.dao;

import system.model.Team;

import java.util.ArrayList;

public class NullTeam extends Team {

    NullTeam() {
        super(0, "n/a", new ArrayList<>(), 0);
    }
}
