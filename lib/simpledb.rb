require 'aws'
require 'yaml'

# Helper class to manage simple_db
module SimpleDB
  
  # Connect to Simple DB, create a domain as identifier if it doesn't exist
  def self.get_domain(identifier)
    config = YAML.load_file("config/aws.yml")
    
    client = AWS::SimpleDB.new(
              :access_key_id => config[ENV['RACK_ENV']]["access_key_id"],
              :secret_access_key => config[ENV['RACK_ENV']]["secret_access_key"])
    puts "Checking the existence of domain"
    domain = client.domains[identifier]
    
    valid_name = true
    begin
    	if domain.exists?
    		puts "The domain '#{identifier}' exists"
    	else
    		domain = client.domains.create identifier 
    		puts "Created new domain '#{identifier}'"
    	end
    rescue AWS::SimpleDB::Errors::InvalidParameterValue => e
    	puts e.message
    	valid_name = false
    end
    
    if valid_name
      return domain
    end
    return nil
  end
end