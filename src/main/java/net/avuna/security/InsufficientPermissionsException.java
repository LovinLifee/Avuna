package net.avuna.security;

public class InsufficientPermissionsException extends Exception {

    public InsufficientPermissionsException(String message) {
        super(message);
    }

    public InsufficientPermissionsException(PlayerPermissions playerPermissions, PlayerPermissions expectedRights) {
        super(String.format("Player has %s rights but was expected to have %s rights", playerPermissions.toString(), expectedRights.toString()));
    }
}
