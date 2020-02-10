package com.labuda.roundup.model;

import java.util.List;

public class Accounts {

    private List<AccountV2> accounts;

    public Accounts() {
    }

    public Accounts(List<AccountV2> accounts) {
        this.accounts = accounts;
    }

    public List<AccountV2> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<AccountV2> accounts) {
        this.accounts = accounts;
    }
}
