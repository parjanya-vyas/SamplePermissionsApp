package com.android.internal.telephony;

import android.app.PendingIntent;

interface ISms {
    void sendTextForSubscriber(in int subId, String callingPkg, String callingAttributionTag,
                in String destAddr, in String scAddr, in String text, in PendingIntent sentIntent,
                in PendingIntent deliveryIntent, in boolean persistMessageForNonDefaultSmsApp,
                in long messageId);
    int getPreferredSmsSubscription();
}