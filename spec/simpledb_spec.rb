require 'spec_helper'
require File.dirname(__FILE__) + '/../lib/simpledb.rb'

describe "SimpleDB" do
  it "should provide functionality for getting SimpleDB domain" do
    SimpleDB.method_defined?(:get_domain)
  end
end