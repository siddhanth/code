PRAGMA foreign_keys = false;

-- ----------------------------------------------------
--  Table structure for "Upcoming Events"
-- ----------------------------------------------------
DROP TABLE IF EXISTS "EVENTS";
CREATE TABLE [EVENTS] (
  [event_id] nvarchar(300) not null
, [event_name] nvarchar(300) not null
, [contacts] nvarchar(1000) not null
, [venue_name] nvarchar(1000) null
, [venue_lat_lon] nvarchar(1000) null
, [timestamp] nvarchar(1000) null
, primary key ([event_id])
);

INSERT INTO EVENTS VALUES('1','Movie Tonight','1,3,4','M G Road','12.323232,91.231231','1231231231231231');
