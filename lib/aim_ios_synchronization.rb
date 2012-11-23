import 'lib/simpledb.rb'
require 'resque-status'

# Synchronize the device_tokens from AppInMap for given iOS applications
class AIM_iOS_Synchronization 
      include Resque::Plugins::Status
  @queue = :synchronization
  
  $last_device_token = nil;
  $migrated_device_count = 0;
  
  # Process device token list
  def self.process_device_tokens(domain,device_tokens)
    device_tokens.each do |device_token|
     	item_name = device_token["device_token"]
     	$last_device_token = item_name;
     	
     	# If token doesn't exist add it
     	unless domain.items[item_name].nil?
     	  item = { :last_registration => Time.now.iso8601, :active => device_token["active"], :alias => device_token["alias"] }
     	  domain.items.create item_name,item
     	else 
     	  # Update it if necessary
     	  item = domain.items[item_name]
     	  if !device_token["active"]
     	    item.attributes.replace(:active => 0, :if => { :active => 1 })
     	  end
     	end
     	
     	$migrated_device_count = $migrated_device_count + 1
    end
  end
  
  def name
    return "AIM iOS Synchronization"
  end

  # Execute the job
  def perform
    tick()
    
    Resque.logger.info("Starting synchronization job for AppInMap");
    Resque.logger.info("Finished migrations. Syncrhonized <0> device tokens")
  end
  
end