create table if not exists currency
(
    id            bigint not null,
    created_at    timestamp,
    updated_at    timestamp,
    current_price double precision,
    name          varchar(255),
    symbol        varchar(255),
    constraint currency_pkey
        primary key (id),
    constraint uk8l6hk5efbtv4vqpbj0h42x5ba
        unique (name, symbol)
);

alter table currency
    owner to postgres;

create table if not exists notification
(
    id          bigint not null,
    created_at  timestamp,
    updated_at  timestamp,
    currency_id varchar(255),
    status      varchar(255),
    user_id     varchar(255),
    constraint notification_pkey
        primary key (id)
);

alter table notification
    owner to postgres;

create table if not exists "user"
(
    id         bigint not null,
    created_at timestamp,
    updated_at timestamp,
    cipher     varchar(255),
    email      varchar(255),
    name       varchar(255),
    type       varchar(255),
    constraint user_pkey
        primary key (id),
    constraint ukhl4ga9r00rh51mdaf20hmnslt
        unique (email)
);

alter table "user"
    owner to postgres;

create table if not exists alert
(
    id           bigint not null,
    created_at   timestamp,
    updated_at   timestamp,
    status varchar(255),
    target_value double precision,
    currency_id  bigint,
    user_id      bigint,
    constraint alert_pkey
        primary key (id),
    constraint fk52sopfgee3ban627mcrve6bd
        foreign key (currency_id) references currency
            on delete cascade,
    constraint fk8aibapj4xxayfescf8fkidap2
        foreign key (user_id) references "user"
            on delete cascade
);

alter table alert
    owner to postgres;

drop function if exists trigger_alert() cascade;

create function trigger_alert() returns trigger
    language plpgsql
as
'begin
    update alert a
    set    status = ''TRIGGERED''
    where  (a.currency_id = NEW.id) and (a.status = ''NEW'') AND (a.target_value <= NEW.current_price);

    RETURN NEW;
end';

alter function trigger_alert() owner to postgres;

create trigger currency_price_update
    after insert or update on currency
    for each row execute procedure trigger_alert();

drop function if exists create_notification_object() cascade;

create function create_notification_object() returns trigger
    language plpgsql
as

'begin
    if NEW.status = ''TRIGGERED'' then
        insert into notification(id, user_id, currency_id, status, created_at, updated_at)
        values (
                   nextval(''hibernate_sequence''),
                   OLD.user_id,
                   OLD.currency_id,
                   ''NEW'',
                   now(),
                   now()
               );
    end if;

    RETURN NEW;
end';

alter function create_notification_object() owner to postgres;

create trigger alert_is_triggered
    after update on alert
    for each row execute procedure create_notification_object();