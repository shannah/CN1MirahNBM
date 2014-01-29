package ca.weblite.codename1.mirah

import com.codename1.ui.*
import com.codename1.ui.layouts.*

class MyForm < Form
  def initialize
    textField = TextField.new()
    setLayout(BorderLayout.new)
    addComponent(BorderLayout.CENTER, textField)
    button = Button.new("Click Me")
    button.addActionListener do |event|
      textField.setText("Mirah Rocks!!")
    end
    textField.setText('Some text here')
    addComponent(BorderLayout.SOUTH, button)
  end
end
