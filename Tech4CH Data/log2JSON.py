import os
import json

class Visitor:
    def __init__(self, number, group_number):
        self.number = number
        self.group_number = group_number
        self.pointOfInterests = list()
        self.presentations = list()

    def add_all_presentations(self, presentations:[]):
        self.presentations.extend(presentations)

    def add_all_pointOfInterest(self, pointOfInterests:[]):
        self.pointOfInterests.extend(pointOfInterests)

class PointOfInterestVisitor:
    def __init__(self, name, start_time, end_time):
        self.name = name
        self.start_time = start_time
        self.end_time = end_time

    def __repr__(self):
        return "name:{0}, start_time:{1}, end_time:{2}".format(self.name, self.start_time, self.end_time)

    def __str__(self):
        return "name:{0}, start_time:{1}, end_time:{2}".format(self.name, self.start_time, self.end_time)


class Presentation:
    def __init__(self, name, start_time, end_time, interruption, rate):
        self.name = name
        self.start_time = start_time
        self.end_time = end_time
        self.interruption = interruption
        self.rate = rate

    def __repr__(self):
        return "name:{0}, start_time:{1}, end_time:{2}, interruption:{3}, rate:{4}".format(self.name, self.start_time, self.end_time,
                                                                         self.interruption, self.rate)

    def __str__(self):
        return "name:{0}, start_time:{1}, end_time:{2}, interruption:{3}, rate:{4}".format(self.name, self.start_time, self.end_time,
                                                                         self.interruption, self.rate)


def process_log_pointOfInterests(path: str, filename: str, visitor: Visitor):
    pointOfInterests = []
    with open(path + filename, "r") as file:
        for line in file:
            stripped_line = line.strip()
            if stripped_line != "presentations":
                tokens = stripped_line.split(",")
                if len(tokens) == 3:
                    start_time = tokens[0]
                    end_time = tokens[1]
                    pos_name = tokens[2]
                    pos = PointOfInterestVisitor(pos_name, start_time, end_time)
                    pointOfInterests.append(pos)
            else:
                break

    visitor.add_all_pointOfInterest(pointOfInterests)
    file.close()


def compute_position_name(path_to_new_file="Data/T4CH_data.json", path_to_positions_file="Data/positions.txt"):
    with open(path_to_new_file) as json_file:
        position_names = set()
        visitors = json.load(json_file)
        json_file.close()
        for v in visitors:
            for p in v["pointOfInterests"]:
                position_names.add(p["name"])

    file_content = ""
    for p in position_names:
        print(p)
        file_content = file_content + p
        file_content = file_content + ","

    pos_names_file = open(path_to_positions_file, "w")
    pos_names_file.write(file_content)
    pos_names_file.close()


def process_log_presentations(path: str, file: str, visitor: Visitor):
    presentations = []
    with open(path + file, "r") as file:
        start_printing = False
        for line in file:
            stripped_line = line.strip()
            if stripped_line == "presentations":
                start_printing = True
            else:
                if stripped_line == "events":
                    break
            if start_printing:
                tokens = stripped_line.split(",")
                if len(tokens) >= 6:
                    start_time = tokens[0]
                    end_time = tokens[1]
                    pos = tokens[2]
                    interruption = tokens[4]
                    rate = tokens[5]
                    if interruption == 'System':
                        interruption = 0 ## Presentation stopped by System
                    else:
                        interruption = 1 ## Presentation stopped by User
                    presentation = Presentation(pos, start_time, end_time, interruption, rate)
                    presentations.append(presentation)

    visitor.add_all_presentations(presentations)
    file.close()

def extract_visitor(filename: str):
    _, visitor_number, group_number = filename.split("_")
    v = Visitor(visitor_number, group_number)
    return v

def create_json(path_to_new_filename, json_filename, path_to_log):
    visitors = []
    for __, ___, files in os.walk(path_to_log):
        for filename in files:
            visitor = extract_visitor(filename[0:-4])
            visitors.append(visitor)
            process_log_pointOfInterests(path_to_log, filename, visitor)
            process_log_presentations(path_to_log, filename, visitor)

    with open(path_to_new_filename + json_filename, 'w') as outfile:
        json_data = json.dumps([v.__dict__ for v in visitors], default=lambda o: o.__dict__, indent=4)
        outfile.write(json_data)
        outfile.close()

def main():
    create_json("Data/", "T4CH_data.json", 'Visitors_Logs/')
    compute_position_name()


if __name__ == "__main__":
    main()