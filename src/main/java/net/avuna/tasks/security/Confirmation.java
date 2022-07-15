package net.avuna.tasks.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public abstract class Confirmation {

    private final int secondsUntilExpire;

    public abstract void onConfirm();

    public void onDeny() {

    }

    public void onExpire() {

    }
}
