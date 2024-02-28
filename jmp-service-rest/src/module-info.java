module org.epm.jmp.service.rest {
    requires org.epm.jmp.cloud.service.impl;
    requires spring.web;
    requires spring.boot.starter.web;
    exports org.epm.jmp.service.rest;
}
