package ua.softserveinc.tc.constants;

public final class EventConstants {

    private EventConstants() {
    }

    public static final class Entity {

        public static final String TABLENAME = "events";

        public static final String ID = "id_event";
        public static final String NAME = "name";
        public static final String START_TIME = "start_time";
        public static final String END_TIME = "end_time";
        public static final String AGE_LOW = "age_low";
        public static final String AGE_HIGH = "age_high";
        public static final String DESCRIPTION = "description";
        public static final String ID_ROOM = "room";

        public static final String REPOSITORY = "eventDao";

        private Entity(){}
    }

    public static final class View {

        public static final String MAIN_PAGE = "index";

        private View(){}
    }
}