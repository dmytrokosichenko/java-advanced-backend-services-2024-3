module org.epm.jmp.cloud.service.impl {
    requires org.epm.jmp.service.api;
    requires spring.boot.starter.data.jpa;
    requires spring.boot;
    requires java.persistence;
    requires h2;
    exports org.epm.jmp.cloud.service.impl;
}
