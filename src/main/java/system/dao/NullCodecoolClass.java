package system.dao;

import system.model.CodecoolClass;

import java.util.ArrayList;

public class NullCodecoolClass extends CodecoolClass {

    NullCodecoolClass() {
        super(0, "n/a", new ArrayList<>());
    }
}
