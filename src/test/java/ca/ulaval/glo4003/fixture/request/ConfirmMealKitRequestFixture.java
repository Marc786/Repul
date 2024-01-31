package ca.ulaval.glo4003.fixture.request;

import ca.ulaval.glo4003.repul.subscription.api.dto.request.ConfirmMealKitRequest;

public class ConfirmMealKitRequestFixture {

    private boolean acceptKit = true;

    public ConfirmMealKitRequest build() {
        return new ConfirmMealKitRequest(acceptKit);
    }

    public ConfirmMealKitRequestFixture withAcceptKit(boolean acceptKit) {
        this.acceptKit = acceptKit;
        return this;
    }
}
