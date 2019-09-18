package bean;

import java.io.Serializable;

public class ZBJTAppEventBean {
    public String event;
    public String callback;

    public static class EventResponse implements Serializable {
        private static final long serialVersionUID = -8316418149649904814L;
        private String event;
        private EventResponse.DataBean data;

        public EventResponse.DataBean getData() {
            return data;
        }

        public void setData(EventResponse.DataBean data) {
            this.data = data;
        }
        public String getEvent() {
            return event;
        }

        public void setEvent(String event) {
            this.event = event;
        }

        public static class DataBean implements Serializable {
            private static final long serialVersionUID = 8478352643245846714L;
            private String status;

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }
        }
    }
}
