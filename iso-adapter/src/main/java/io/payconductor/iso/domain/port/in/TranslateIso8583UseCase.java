package io.payconductor.iso.domain.port.in;

/**
 * Primary port — translates an inbound ISO 8583 message into a PayConductor payment command
 * and forwards it to payment-service.
 *
 * ISO 8583 specifics (MTI, field layout, BCD vs ASCII encoding) are confined to the
 * infrastructure.iso8583 package. This port deals in terms of intent, not wire format.
 */
public interface TranslateIso8583UseCase {

    record Iso8583Message(byte[] rawMessage, String connectionId) {}
    record PaymentCommand(String merchantId, long amountMinorUnit, String currency, String rrn) {}

    PaymentCommand translate(Iso8583Message message);
}
