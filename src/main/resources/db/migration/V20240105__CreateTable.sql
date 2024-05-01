-- Create Gig table
CREATE TABLE IF NOT EXISTS Gig (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    artist VARCHAR(255) NOT NULL,
    venue VARCHAR(255) NOT NULL,
    location VARCHAR(255) NOT NULL,
    date DATETIME NOT NULL,
    favourite BOOLEAN NOT NULL
);

-- Create Moment table
CREATE TABLE IF NOT EXISTS Moment (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    description VARCHAR(255) NOT NULL,
    gig_id BIGINT NOT NULL,
    FOREIGN KEY (gig_id) REFERENCES Gig(id)
);

-- Insert sample data into Gig table
INSERT INTO Gig (artist, venue, location, date, favourite) VALUES
('Artist 1', 'Venue 1', 'Location 1', '2024-05-01 20:00:00', true),
('Artist 2', 'Venue 2', 'Location 2', '2024-05-02 19:00:00', false),
('Artist 3', 'Venue 3', 'Location 3', '2024-05-03 18:00:00', true);

-- Insert sample data into Moment table
INSERT INTO Moment (description, gig_id) VALUES
('Description 1', 1),
('Description 2', 1),
('Description 3', 2),
('Description 4', 3);
