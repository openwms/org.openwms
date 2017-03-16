/*
 * 	Test Object
 */
var	test = {
		selfTest : function() {
			1+2;
		},
		subscribe: function() {
			1+1;
		},
		scopeTest: function() {
			this.subscribe();
		}
	}

/*
 * 	Core Tests
 */
describe("Radio Core Test", function() {
  beforeEach(function() {
			f = function(){};
			f2 = function() {};
			f3 = function() {};
  });
 	 describe("When radio is loaded", function() {
		    it("should be global and accessible", function() {
			  	expect(radio).toBeDefined();
		    });
	
			it("should return a version number", function() {
				 expect(radio.$.version).toBeDefined();
			});
	  });
	
	 describe("radio() method", function() {
		    it("should set the channel name", function() {
				radio('channel1');
			  	expect(radio.$.channelName).toBe('channel1');
		    });
			it("requires a string", function() {
				radio('channel1');
			  	expect(radio.$.channelName).toBe('channel1');
		    });
	  });

	 describe("radio.channel method", function() {
		 	it("should set the channel name", function() {
				radio('test').channel('channel1');
			  	expect(radio.$.channelName).toBe('channel1');
		    });
		    it("should make multiple channels chainable", function() {
				radio('channel1').subscribe(f,f).channel('channel2').subscribe(f);
			  	expect(radio.$.channelName).toBe('channel2');
				expect(radio.$.channels.channel1.length).toBe(2);
				expect(radio.$.channels.channel2.length).toBe(1);
		    });
			it("should create a new channel if it doesn't exists", function() {
				expect(radio.$.channels['test']).not.toBeDefined();
				radio('test');
				expect(radio.$.channels['test']).toBeDefined();
			});

	  });
	
	
 	describe("radio.subscribe method", function() {
					it("should register a single listener to a channel", function() {
						 var test = radio('channel1').subscribe(f);
						 expect(radio.$.channels.channel1.length).toBe(1);
					});
					it("should subscribe multiple listeners to a channel", function() {
						 radio('channel1').subscribe(f,f,f);
						 expect(radio.$.channels.channel1.length).toBe(3);
					});
					it("should suppport subscribeing listerners with setting the context of 'this'", function() {
							 radio('channel1').subscribe([test.selfTest, test]);
							 expect(radio.$.channels.channel1.length).toBe(1);
					});
					it("should support a combination of subscribeing functions in different ways", function() {
								 radio('channel1').subscribe(f,[test.selfTest, test], f2);
								 expect(radio.$.channels.channel1.length).toBe(3);
					});
					it("should not allow a non-function or array to be subscribeed", function() {
									 radio('channel1').subscribe("string", f);
									 expect(radio.$.channels.channel1.length).toBe(1);
									 radio('channel1').subscribe(2, f);
									 expect(radio.$.channels.channel1.length).toBe(2);
					});
					it("should be chainable", function() {
						 radio('channel1').subscribe(f).subscribe(f2);
						 expect(radio.$.channels.channel1.length).toBe(2);
					});
	  });

  	describe("radio.unsubscribe method", function() {
					it("should unsubscribe a listener from a channel", function() {
						 radio('channel1').subscribe(f);
						 expect(radio.$.channels.channel1.length).toBe(1);
						 radio('channel1').unsubscribe(f);
						 expect(radio.$.channels.channel1.length).toBe(0);
					});
					it("should unsubscribe multiple listeners from a channel", function() {
						 radio('channel1').subscribe(f,f2,f3);
						 expect(radio.$.channels.channel1.length).toBe(3);
						 radio('channel1').unsubscribe(f);
						 expect(radio.$.channels.channel1.length).toBe(2);
					});
					it("should unsubscribe duplicate listeners from a channel", function() {
							//check 1
							 radio('channel1').subscribe(f,f,f);
							 expect(radio.$.channels.channel1.length).toBe(3);
							 radio('channel1').unsubscribe(f);
							 expect(radio.$.channels.channel1.length).toBe(0);
							//check2 
							 radio('channel1').subscribe(f,f,f, f2, f3, [test.selfTest, test], test.selfTest);
							 expect(radio.$.channels.channel1.length).toBe(7);
							 radio('channel1').unsubscribe(f);
							 expect(radio.$.channels.channel1.length).toBe(4);
							 radio('channel1').unsubscribe(test.selfTest);
							 expect(radio.$.channels.channel1.length).toBe(2);
							 radio('channel1').unsubscribe(f2, f3);
						     expect(radio.$.channels.channel1.length).toBe(0);
					});
					it("should unsubscribe listeners subscribe with context from channel", function() {
						 radio('channel1').subscribe([test.selfTest, test], f);
						 expect(radio.$.channels.channel1.length).toBe(2);
						 radio('channel1').unsubscribe(test.selfTest);
						 expect(radio.$.channels.channel1.length).toBe(1);
					});
					it("should not throw an error if unsubscribe an item that doesn't exists", function() {
								 radio('newChannel').unsubscribe(f2);
					});
					it("should be chainable", function() {
							 radio('channel1').subscribe(f, f2, f3).unsubscribe(f2);
							 expect(radio.$.channels.channel1.length).toBe(2);
					});
					
	  });

  	describe("radio.broadcast method", function() {
					it("should call each listener - test 1", function() {
						spyOn(test, 'subscribe');
						spyOn(test, 'selfTest');
						radio('channel1').subscribe([test.selfTest, test], [test.subscribe, test]);
						expect(radio.$.channels.channel1.length).toBe(2);
						radio('channel1').broadcast('test');
						expect(test.selfTest).toHaveBeenCalled();
						expect(test.subscribe).toHaveBeenCalled();
						expect(test.selfTest.callCount).toBe(1);
						expect(test.subscribe.callCount).toBe(1);
						//reset methods
						test.selfTest.reset();
						test.subscribe.reset();
					});
					it("should call each listener - test 2", function() {
							spyOn(window, 'f');
							spyOn(window, 'f2');
						    spyOn(window, 'f3');
							radio('channel1').subscribe(f, f, f, f, f2, f2, f3).broadcast('test');
							expect(window.f.callCount).toBe(4);
							expect(window.f2.callCount).toBe(2);
							expect(window.f3.callCount).toBe(1);	
							window.f.reset();
							window.f2.reset();
							window.f3.reset();	
					});
					it("should pass it's broadcast arguments to the listener", function() {
							spyOn(test, 'subscribe');
							spyOn(test, 'selfTest');
							radio('channel1').subscribe([test.selfTest, test]);
							radio('channel2').subscribe([test.subscribe, test]);
							radio('channel1').broadcast('data1', ['somearray','item2'], 'data3');
							radio('channel2').broadcast('data1');
							expect(test.selfTest).toHaveBeenCalledWith('data1', ['somearray','item2'], 'data3');
							expect(test.subscribe).toHaveBeenCalledWith('data1');
					});
					
	
					it("should call the listener and maintain it's set scope", function() {
								spyOn(test, 'subscribe');
								//subscribe the scopeTest method.  Scope test sets the context 'test' as this and calls the subscribe method
								radio('channel1').subscribe([test.scopeTest, test]).broadcast('test');
								expect(test.subscribe.callCount).toBe(1);
								
					});
					
					it("should call the listener and set the scope as window if it wasn't specified", function() {
								spyOn(window, 'f');
								//subscribe the scopeTest method.  Scope test sets the context 'test' as this and calls the subscribe method
								radio('channel1').subscribe(f).broadcast('test');
								expect(window.f.callCount).toBe(1);
								window.f.reset();
					});
					
	  });
	
	
 	 describe("radio subscribe unsubscribe and broadcast methods ", function() {
					it("should work all together in a chain", function() {
						//test 1
						 radio('channel1').subscribe(f, f2, f3).unsubscribe(f);
						 expect(radio.$.channels.channel1.length).toBe(2);
						
						//test2
						 spyOn(window, 'f');
						 spyOn(window, 'f2');
						 spyOn(window, 'f3');
						 radio('channel2').subscribe(f, f, f, f, f2).unsubscribe(f, f2).subscribe(f3).broadcast('test');
						 expect(window.f2.callCount).toBe(0);
						 expect(window.f3.callCount).toBe(1);
						 window.f.reset();
						 window.f2.reset();
						 window.f3.reset();
					});
	  });

	
	afterEach(function() {
		//clear out the channels.
		radio.$.channels = [];
	});
});




 

