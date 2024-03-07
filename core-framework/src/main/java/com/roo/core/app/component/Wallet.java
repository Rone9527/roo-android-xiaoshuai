package com.roo.core.app.component;

public class Wallet {

    public static final String NAME = "module_wallet";

    public static class Param {

        public static String TransferRecordDataBean = "TransferRecordDataBean";
    }

    public static class Action {
        public static final String AssetSelectActivity = "AssetSelectActivity";

        public static final String WalletListActivity = "WalletListActivity";

        public static final String WalletManagerActivity = "WalletManagerActivity";

        public static final String WalletCreateActivity = "WalletCreateActivity";

        public static final String TransferDetailActivity = "TransferDetailActivity";

        public static final String WalletCreateNormalActivity = "WalletCreateNormalActivity";

        public static final String WalletFragment = "WalletFragment";

    }

    public static class Result {
        public static final String RESULT = "RESULT";

    }

}