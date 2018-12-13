# mpp2xls
Convert mpp to xls

## Usage

1. git clone https://github.com/dcolley/mpp2xls this project to local
2. Load the project in Eclipse
3. Edit the config.xml, change the column order if you like..!
4. Run the MPPReadToXML.java via "Run As Application..."

## Features

The resulting .xls file will contain:

1. Sheet of Tasks (fields as per config.xml)
2. Resources (TODO)
3. Tables (TODO)
4. Calendars (TODO)
5. Views (TODO)
6. Custom Fields (TODO)

### TODO

1. Create custom getters for net.sf.mpxj.Duration, and others
2. Using the same pattern, implement sheets for Resources, Tables, Calendars & Views
3. Handle List of="OtherClass"

### Credits

- Apache POI - https://poi.apache.org/
- MPXJ - http://www.mpxj.org/
