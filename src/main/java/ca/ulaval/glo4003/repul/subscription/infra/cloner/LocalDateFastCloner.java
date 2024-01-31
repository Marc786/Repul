package ca.ulaval.glo4003.repul.subscription.infra.cloner;

import com.rits.cloning.IDeepCloner;
import com.rits.cloning.IFastCloner;
import java.time.LocalDate;
import java.util.Map;

public class LocalDateFastCloner implements IFastCloner {

    public Object clone(
        final Object t,
        final IDeepCloner cloner,
        final Map<Object, Object> clones
    ) {
        final LocalDate original = (LocalDate) t;
        return LocalDate.of(
            original.getYear(),
            original.getMonth(),
            original.getDayOfMonth()
        );
    }
}
