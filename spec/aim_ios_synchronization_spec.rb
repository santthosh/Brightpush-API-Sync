require 'spec_helper'
require File.dirname(__FILE__) + '/../lib/aim_ios_synchronization.rb'

describe "AIM_iOS_Synchronization" do
  it "should provide functionality for synchronizing new and updated iOS device tokens to brightpush" do
    AIM_iOS_Synchronization.method_defined?(:perform)
  end
end