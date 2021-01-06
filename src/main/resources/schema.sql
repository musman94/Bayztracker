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
    alert_status varchar(255),
    target_value double precision,
    currency_id  bigint,
    user_id      bigint,
    constraint alert_pkey
        primary key (id),
    constraint fk52sopfgee3ban627mcrve6bd
        foreign key (currency_id) references currency,
    constraint fk8aibapj4xxayfescf8fkidap2
        foreign key (user_id) references "user"
);

drop function if exists trigger_alert() cascade;

create function trigger_alert() returns trigger
    language plpgsql
as

'begin
    update alert a
    set    alert_status = ''TRIGGERED''
    where  (a.currency_id = NEW.id) and (a.alert_status = ''NEW'') AND (a.target_value <= NEW.current_price);

    RETURN NEW;
end';

alter function trigger_alert() owner to postgres;

drop trigger if exists currency_price_update on currency;

create trigger currency_price_update
    after insert or update on currency
    for each row execute procedure trigger_alert();
