package telegony.dataaccess.common;

/**
 * Описание типа зоны
 * @author Ivashin Alexey
 */
@Deprecated
public class ZoneType extends TransientEnum {

    public final static ZoneType GENERAL_ZONE_TYPE = new ZoneType(1L, "Общий тип");
    public final static ZoneType OUTER_ZONE_TYPE = new ZoneType(2L, "Внешняя среда");
    public final static ZoneType INNER_ZONE_TYPE = new ZoneType(3L, "Помещение");
//    static {
//        RepositoryProvider.getRepository(ZoneType.class).save(GENERAL_ZONE_TYPE);
//    }

    public ZoneType() {
    }

    private ZoneType(Long id, String description) {
        super(id, description);
    }
}
