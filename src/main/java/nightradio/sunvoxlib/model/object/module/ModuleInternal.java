package nightradio.sunvoxlib.model.object.module;

import nightradio.sunvoxlib.model.object.Slot;

public class ModuleInternal extends Module {

    public ModuleInternal(Slot slot, ModuleInternalType type, String name, int x, int y, int z) {
        super(slot, type.getValue(), name, x, y, z);
    }

    public ModuleInternal(Slot slot, String name) {
        super(slot, name);
    }
}
