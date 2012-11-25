require 'spec_helper'
require File.dirname(__FILE__) + '/../lib/aim_token_synchronization.rb'

describe "AIM_Token_Synchronization" do
  it "should provide functionality for synchronizing new and updated device tokens to brightpush" do
    AIM_Token_Synchronization.method_defined?(:perform)
  end
end