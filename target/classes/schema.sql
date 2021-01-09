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
                   OLD.currency_id,
                   OLD.user_id,
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