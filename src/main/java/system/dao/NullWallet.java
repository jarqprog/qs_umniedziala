package system.dao;

import system.model.Wallet;

public class NullWallet extends Wallet {

    NullWallet() {
        super();
    }

    @Override
    public int getWalletId() {
        return 0;
    }
}
